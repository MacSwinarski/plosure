(ns plosure.forclojure.splitbytype)


(def st1
  (fn [coll]
    (let [types (set (map type coll))]
      (map (fn [t] (filter (fn [e] (= (type e) t)) coll)) types))))


(def maximental #(vals (group-by type %)))

(def st maximental)

(println "group-by" (group-by #(< 0 %) [1 2 -4 5 -6 10 -100] ))
(println "group-by" (group-by type [1 :a 2 :b 3 :c "a1" "dwdwed"] ))

(println "vals" (vals {:a [1 2 3] :b [4 5 6] :c [7 8 9]}))


(println "1: " (st [1 :a 2 :b 3 :c]))
(println "2: " (st [:a "foo"  "bar" :b]))
(println "3: " (st [[1 2] :a [3 4] 5 6 :b]))


(println (= (set (st [1 :a 2 :b 3 :c])) #{[1 2 3] [:a :b :c]}))


(println (= (set (st [:a "foo"  "bar" :b])) #{[:a :b] ["foo" "bar"]}))


(println (= (set (st [[1 2] :a [3 4] 5 6 :b])) #{[[1 2] [3 4]] [:a :b] [5 6]}))


(defn -main []
  (println "Split by type"))
