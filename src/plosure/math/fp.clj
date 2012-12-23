(ns plosure.math.fp
  (:import java.lang.Math))

(defn fsek [f x]
  (let [fx (f x)]
    (cons fx (lazy-seq (fsek f fx)))))

(defn fp
  "finds function fixed point"
  ([f] (fp f 1))
  ([f x] (let [fsek (fsek f x)
        fsek-diff (map #(/ %1 %2) fsek (rest fsek))
        idx-fsek-diff (map-indexed vector fsek-diff)
        idx (first (first (filter #(< (Math/abs (second %)) (+ 1.0 1e-10)) idx-fsek-diff)))]
    (nth fsek idx))))

(defn avg [x y]
  (/ (+ x y) 2.0))

(defn sqrtfn [n]
  {:pre [(< 0 n)]}
  (fn [x] (avg (/ n x) x)))

(defn -main []
  (println "fp(sqrtfn(2)) =" (fp (sqrtfn 2)))
  (println "fp(sqrtfn(4)) =" (fp (sqrtfn 4)))
  (println "fp(sqrtfn(5)) =" (fp (sqrtfn 5)))
  (println "fp(sqrtfn(9)) =" (fp (sqrtfn 9)))
  (println "fp(sqrtfn(36)) =" (fp (sqrtfn 36)))
  (println "fp(sqrtfn(100)) =" (fp (sqrtfn 100))))




