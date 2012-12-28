(ns plosure.forclojure.flipout)

(def f-o
  (fn [f]
    (fn [& args] (apply f (reverse args)))))

(defn -main []
  (do
    (println "1: " (= 3 ((f-o nth) 2 [1 2 3 4 5])))
    (println "2: " (= true ((f-o >) 7 8)))
    (println "3: " (= 4 ((f-o quot) 2 8)))
    (println "4: " (= [1 2 3] ((f-o take) [1 2 3 4 5] 3)))))


;maximental's solution:
;
;(partial partial
;  (comp (partial apply apply)
;    (juxt first
;      (comp (juxt second first)
;        rest))
;    list))