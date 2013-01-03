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

(println "65")

(def f65
  (fn [coll1]
    (let [size1 (count coll1)
          coll2 (into coll1 [[:t1 :t1] [:t1 :t1] [:t1 :t2]])
          size2 (count coll2)]
        (cond
          (= (+ 1 size1) size2) :map
          (= (+ 2 size1) size2) :set
          (= (first coll2) [:t1 :t2]) :list
          (= (last coll2) [:t1 :t2]) :vector
          :default :unknown ))))

(println (= :map (f65 {:a 1, :b 2})))
(println (= :list (f65 (range (rand-int 20)))))
(println (= :vector (f65 [1 2 3 4 5 6])))
(println (= :set (f65 #{10 (rand-int 5)})))
(println (= [:map :set :vector :list] (map f65 [{} #{} [] ()])))

(println "80")

(def f80
  (fn [n]
    (= n (reduce + (filter #(= 0 (mod n %)) (range 1 n))))))


(println (f80 6))
(println (f80 7))

(println "77")

(def f77
  #(set (filter (fn [coll] (< 1 (count coll))) (map set (vals (group-by sort %))))))

(println (f77 ["veer" "lake" "item" "kale" "mite" "ever"]))

(println (= (f77 ["meat" "mat" "team" "mate" "eat"])
    #{#{"meat" "team" "mate"}}))

(println (= (f77 ["veer" "lake" "item" "kale" "mite" "ever"])
    #{#{"veer" "ever"} #{"lake" "kale"} #{"mite" "item"}}))

(println "60")

;(def f60
;  (fn [f coll]
;    (reduce (fn [results b]
;              (let [a (last results)
;                    c (if a (f a b) (f b))]
;                (conj results c))) [] coll)))
; watch out! reduce is !ofkoz! hue hue hue not lazy - will not work on infinite seqs

(def f60
  (fn f60
    ([f coll] (f60 f nil coll))
    ([f start coll] (let [reds (map
                      (fn [e i] (if start (reduce f start (take i coll)) (reduce f (take i coll))))
                      coll (rest (range)))]
                      (if start (conj reds start) reds)))))

(def maximental
  (fn g
  ([f [x & s]] (g f x s))
  ([f a [x & s]]
    (lazy-seq
      (cons a (if x (g f (f a x) s)))))))

(println (reductions + (range 5)))
(println (f60 + (range 5)))
(println (reductions conj [1] [2 3 4]))
(println (f60 conj [1] [2 3 4]))
(println (reductions * 2 [3 4 5]))
(println (f60 * 2 [3 4 5]))


(println (= (take 5 (f60 + (range))) [0 1 3 6 10]))
(println (= (f60 conj [1] [2 3 4]) [[1] [1 2] [1 2 3] [1 2 3 4]]))
(println (= (last (f60 * 2 [3 4 5])) (reduce * 2 [3 4 5]) 120))



(println "102")

(def f102
  (fn [s]
    (apply str
      (reduce
        (fn [coll b] (if (= \- (last coll)) (conj (vec (butlast coll)) (Character/toUpperCase b)) (conj coll b)))
        []
        (seq s)))))

(println (f102 "multi-word-key"))

(println (= (f102 "something") "something"))


(println "86")


(def f86
  (fn f
    ([n lim] (if (= 0 lim)
               false
               (let [text-n (str n)
                     sqr-dig (reduce + (map #(let [i (- (int %) (int \0))] (* i i)) text-n))]
                    (if (= 1 sqr-dig) true (recur sqr-dig (dec lim))))))
    ([n] (f n 1000))))

(println (f86 986543210))

(println (= (f86 7) true))
(println (= (f86 986543210) true))
(println (= (f86 2) false))
(println (= (f86 3) false))

(println "78")

(def f78
  (fn [f & args]
    (let [r (if (not (nil? args)) (apply f args) (f))]
       (if (not (fn? r)) r (recur r nil)))))



(println (= (letfn [(my-even? [x] (if (zero? x) true #(my-odd? (dec x))))
           (my-odd? [x] (if (zero? x) false #(my-even? (dec x))))]
     (map (partial f78 my-even?) (range 6)))
  [true false true false true false]))



