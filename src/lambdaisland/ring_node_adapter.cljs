(ns lambdaisland.ring-node-adapter
  "Ring adapter for Node.js"
  (:require
   ["http" :as http]
   [clojure.string :as str]
   [lambdaisland.ring.core.protocols :as protocols]))

(defn- normalize-headers
  [^js headers]
  (reduce (fn [m k]
            (let [v (unchecked-get headers k)]
              (assoc m k
                     (if (array? v)
                       (str/join (if (= "cookie" k) ";" ",") v)
                       v))))
          {}
          (js/Object.keys headers)))

(defn- build-request-map
  [^js req]
  (let [^js url (js/URL. (.-url req) "http://_")]
    {:server-port    (.. req -socket -localPort)
     :server-name    (.. req -socket -localAddress)
     :remote-addr    (.. req -socket -remoteAddress)
     :uri            (.-pathname url)
     :query-string   (when-let [s (.-search url)]
                       (when (not= s "")
                         (.slice s 1)))
     :scheme         :http
     :request-method (keyword (.toLowerCase (.-method req)))
     :headers        (normalize-headers (.-headers req))
     :protocol       (str "HTTP/" (.-httpVersion req))
     :body           req}))

(defn- send-response
  [^js res {:keys [status headers body] :as response}]
  (.writeHead res (int status) (clj->js headers))
  (protocols/write-body-to-stream body response res))

(defn- make-node-handler [handler]
  (fn ^:async node-handler [req res]
    (try
      (let [ring-req (build-request-map req)
            ring-res (await (handler ring-req))]
        (send-response res ring-res))
      (catch :default e
        (js/console.error e)
        (send-response
         res
         {:status 500
          :headers {"content-type" "text/plain"}
          :body (str "Internal Server Error\n\n" (.-message e))})))))

(defn run-adapter
  "Start a synchronous Ring adapter on the given port."
  [handler {:keys [port]}]
  (let [server (.createServer http (make-node-handler handler))]
    (.listen server port)
    server))

(comment
  (run-adapter (fn [req]
                 (def +req req)
                 {:status 200
                  :body (pr-str req)})
               {:port 9993})

  (run-adapter (fn [req]
                 (def +req req)
                 (js/Promise. (fn [resolve reject]
                                (resolve {:status 200
                                          :body (pr-str req)}))))
               {:port 9994}))
