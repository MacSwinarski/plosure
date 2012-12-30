(ns plosure.forclojure.bucket)

(defn -main []
  (println "Bucket"))


(def f55a
  (fn [coll]
    (into {}
      (map #(vector % (count (filter (fn [x] (= x %)) coll))) (set coll)))))

(def f55b
  (fn [coll]
    (reduce (fn [m b] (assoc m b (inc (get m b 0)))) {} coll)))


(def f55 f55b)

(println (f55 [1 1 2 3 2 1 1]))
(println (f55 [:b :a :b :a :b]))
(println (f55 '([1 2] [1 3] [1 3])))


(println "56")

(def f56
  (fn f [[x & xs]]
    (if x (cons x (f (filter #(not= x %) xs))))))


(println (f56 [1 2 1 3 1 2 4]))


(println (= (f56 [1 2 1 3 1 2 4]) [1 2 3 4]))
(println (= (f56 [:a :a :b :b :c :c]) [:a :b :c]))
(println (= (f56 '([2 4] [1 2] [1 3] [1 3])) '([2 4] [1 2] [1 3])))
(println (= (f56 (range 50)) (range 50)))

(println "58")

(def f58
  (fn [& fns]
    (fn [& args]
      (first (reduce (fn [a f]
        (list (apply f a))) args (reverse fns))))))

(println "1: " ((f58 rest reverse) [1 2 3 4]))

(println (= [3 2 1] ((f58 rest reverse) [1 2 3 4])))
(println (= 5 ((f58 (partial + 3) second) [1 2 3 4])))
(println (= true ((f58 zero? #(mod % 8) +) 3 5 7 9)))
(println (= "HELLO" ((f58 #(.toUpperCase %) #(apply str %) take) 5 "hello world")))

(println "59")

(def f59
  (fn [& fns]
    (fn [& args]
      (map #(apply % args) fns))))

(println "1: " ((f59 + max min) 2 3 5 1 6 4))

(println (= [21 6 1] ((f59 + max min) 2 3 5 1 6 4)))
(println (= ["HELLO" 5] ((f59 #(.toUpperCase %) count) "hello")))
(println (= [2 6 4] ((f59 :a :c :b) {:a 2, :b 4, :c 6, :d 8 :e 10})))

(println "54")

(def f54
  (fn [n coll]
    (apply map list
      (for [i (range n)] (take-nth n (drop i coll))))))

(println "1: " (f54 3 (range 9)))

(println (= (f54 3 (range 9)) '((0 1 2) (3 4 5) (6 7 8))))
(println (= (f54 2 (range 8)) '((0 1) (2 3) (4 5) (6 7))))
(println (= (f54 3 (range 8)) '((0 1 2) (3 4 5))))


(println "67")

(def f67
  (fn [n]
    (take n
      (filter
        (fn [p] (every? false? (map (fn [i] (= 0 (mod p i))) (range 2 p))))
        (drop 2 (range))))))

(println "1: " (f67 2))
(println "2: " (f67 5))

(println (= (f67 2) [2 3]))
(println (= (f67 5) [2 3 5 7 11]))
(println (= (last (f67 100)) 541))

(println "76")

(defn f76 [c]  )


(= __
  (letfn
    [(foo [x y] #(bar (conj x y) y))
     (bar [x y] (if (> (last x) 10)
                  x
                  #(foo x (+ 2 y))))]
    (trampoline foo [] 1)))



