(ns repro
  (:require [datahike.api :as d]))

(defn repro
  []
  (let [config {:store {:backend :file
                      :path   "example.backup"}}
       conn   (d/connect config)]

    ;; The db works for pull and other operations.
    (println (d/pull @conn '[*] [:node/title "September 20, 2021"]))

    ;; But d/with throws an exception:
    ;; Execution error (IllegalArgumentException) at hitchhiker.tree.node/eval14856$fn$G (node.cljc:10).
    ;; No implementation of method: :-resolve-chan of protocol: #'hitchhiker.tree.node/IAddress found for class: clojure.lang.PersistentArrayMap
    (d/with @conn [{:node/title "September 24, 2021",
                    :block/uid "09-24-2021",
                    :block/children [{:block/string "",
                                      :block/uid "119f87b04",
                                      :block/order 0,
                                      :block/open true,
                                      :create/time 1632481485181,
                                      :edit/time 1632481485181}],
                    :create/time 1632481485181,
                    :edit/time 1632481485181}])
    (d/release conn)))

(defn run [_]
  (repro))

(comment
  (repro))

