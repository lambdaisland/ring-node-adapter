(ns lambdaisland.ring.core.protocols
  "cljs port of the original ring.core.protocols

  Intended as a placeholder until the day Ring gets a platform-agnostic
  version."
  (:require ["stream" :as node-stream]))

(defprotocol StreamableResponseBody
  "A protocol for writing data to the response body via a writable stream."
  (write-body-to-stream [body response output-stream]
    "Write a value representing a response body to a writable stream. The stream
    will be ended after the value has been written."))

(defn- get-charset
  [response]
  (when-let [content-type (get-in response [:headers "content-type"])]
    (second (re-find #"(?i)charset=([^\s;]+)" content-type))))

(extend-protocol StreamableResponseBody
  js/Buffer
  (write-body-to-stream [body _ ^js output-stream]
    (.end output-stream body))
  js/Uint8Array
  (write-body-to-stream [body _ ^js output-stream]
    (.end output-stream body))
  string
  (write-body-to-stream [body response ^js output-stream]
    (if-let [charset (get-charset response)]
      (.end output-stream (js/Buffer.from body charset))
      (.end output-stream body)))
  object
  (write-body-to-stream [body response ^js output-stream]
    (if (seqable? body)
      (let [charset (get-charset response)]
        (doseq [chunk body]
          (.write output-stream
                  (if charset
                    (js/Buffer.from (str chunk) charset)
                    (str chunk))))
        (.end output-stream))
      (if-let [charset (get-charset response)]
        (.end output-stream (js/Buffer.from (str body) charset))
        (.end output-stream (str body)))))
  node-stream/Readable
  (write-body-to-stream [body _ ^js output-stream]
    (.pipe body output-stream))
  nil
  (write-body-to-stream [_ _ ^js output-stream]
    (.end output-stream)))
