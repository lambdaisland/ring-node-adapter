(ns lambdaisland.ring-node-adapter
  "Ring adapter for Node.js"
  (:require ["http" :as http]))

(defn- build-request-map
  [^js req]
  {:server-port    (.. req -socket -localPort)
   :server-name    (.. req -socket -localAddress)
   :remote-addr    (.. req -socket -remoteAddress)
   :uri            (.. req -url)
   :scheme         :http
   :request-method (keyword (.toLowerCase (.-method req)))
   :headers        (js->clj (.-headers req))
   :protocol       (str "HTTP/" (.-httpVersion req))
   :body           req})

(defn- send-response
  [^js res {:keys [status headers body]}]
  (def +res res)
  (.writeHead res (int status) (clj->js headers))
  (if body
    (.end res (str body))
    (.end res)))

(defn run-adapter
  "Start a synchronous Ring adapter on the given port."
  [handler {:keys [port]}]
  (let [server (.createServer http (fn [req res]
                                     (->> (build-request-map req)
                                          (handler)
                                          (send-response res))))]
    (.listen server port)
    server))

(comment
  (run-adapter (fn [req]
                 (def +req req)
                 {:status 200
                  :body "OK!"})
               {:port 9999}))
