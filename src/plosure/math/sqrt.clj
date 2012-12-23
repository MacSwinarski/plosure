(ns plosure.math.sqrt)


(defn sqrt [x]
  (let [ys (rest (range))
        zs (map #(/ x %) ys)
        avgs (map #(/ (+ %1 %2) 2) ys zs)
        decreasing (map #(> %1 %2) avgs (rest avgs))
        decreasing-idx (map-indexed vector decreasing)
        idx (first (first (filter #(false? (second %)) decreasing-idx)))]
    (nth avgs idx)))

(defn -main[]
  (do
    (println "sqrt(1) =" (sqrt 1))
    (println "sqrt(4) =" (sqrt 4))
    (println "sqrt(5) =" (sqrt 5))
    (println "sqrt(9) =" (sqrt 9))
    (println "sqrt(10) =" (sqrt 10))
    (println "sqrt(15) =" (sqrt 15))
    (println "sqrt(36) =" (sqrt 36))
    (println "sqrt(81) =" (sqrt 81))
    (println "sqrt(100) =" (sqrt 100))
    (println "sqrt(1024) =" (sqrt 1024))
    (println "sqrt(1000000) =" (sqrt 1000000))))



