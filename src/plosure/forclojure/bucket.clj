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

(def f78a
  (fn [f & args]
    (let [r (apply f args)]
       (if (fn? r) (recur r []) r))))

;(def maximental #(loop [f (% %2)] (if (fn? f) (recur (f)) f)))
;(def pcl (fn [& as]
;   (let [r (apply (first as) (rest as))]
;     (if (fn? r) (recur [r]) r))))

(def f78b #(loop [f (%1 %2)] (if (fn? f) (recur (f)) f)))

(def f78 f78a)

(println (= (letfn [(my-even? [x] (if (zero? x) true #(my-odd? (dec x))))
           (my-odd? [x] (if (zero? x) false #(my-even? (dec x))))]
     (map (partial f78 my-even?) (range 6)))
  [true false true false true false]))


(println "98")

(def f98 #(set (map set (vals (group-by %1 %2)))))

(println (f98 #(* % %) #{-2 -1 0 1 2}))

(println (= (f98 #(* % %) #{-2 -1 0 1 2})
    #{#{0} #{1 -1} #{2 -2}}))
(println (= (f98 #(rem % 3) #{0 1 2 3 4 5 })
    #{#{0 3} #{1 4} #{2 5}}))
(println (= (f98 identity #{0 1 2 3 4})
    #{#{0} #{1} #{2} #{3} #{4}}))
(println (= (f98 (constantly true) #{0 1 2 3 4})
    #{#{0 1 2 3 4}}))


(println "85")

;(defn f85 [coll]
;  (let [n (count coll)
;        c (for [i (range 0 (inc n))] (take i coll))]
;    (conj (set (flatten (map #(map (fn [cc] (set (conj cc %))) c) coll))) #{} )))

;(defn f85 [coll]
;  (let [rotate (fn [coll n] (if (zero? n) coll (recur (cons (last coll) (butlast coll)) (dec n))))
;        coll (cons nil coll)
;        colls (for [n (range 0 (count coll))] (rotate coll n))]
;    (apply map vector colls)
;    ))


(def comb1
  (fn comb [coll]
    (if (seq (rest coll))
      (let [comb-rest (comb (rest coll))]
        (concat (map #(set (cons (first coll) %)) comb-rest) comb-rest))
      #{ #{} #{(first coll)} } )))

;(def comb2
;  (fn comb [coll]
;    (if (first coll)
;      (let [comb-rest (comb (rest coll))]
;        (concat (map #(set (cons (first coll) %)) comb-rest) comb-rest))
;        #{ #{} } )))

(def comb2
  (fn comb [coll]
    (let [x (first coll) xs (rest coll)]
      (if x
        (let [comb-rest (comb xs)] (set (concat (map #(set (cons x %)) comb-rest) comb-rest)))
        #{#{}} ))))


(def maximental
  (fn [s]
    (reduce
      (fn [a b]
        (into a (map #(conj % b) a)))
        #{#{}}
      s)))

(def mac2
  (fn [coll]
    (reduce
      (fn [result b] (into result (map #(set (cons b %)) result)))
      #{#{}}
      coll)))

; conj will return same type of collection, cons returns seq

(def mac3
  (fn [coll]
    (reduce
      (fn [result b] (into result (map #(conj % b) result)))
        #{#{}}
      coll)))

;(def f85 comb2)
(def f85 mac3)


(println "comb1a" (comb1 #{1}))
(println "comb2a" (comb1 #{1 2}))
(println "comb3a" (comb1 #{1 2 3}))

(println "comb1b" (comb2 #{1}))
(println "comb2b" (comb2 #{1 2}))
(println "comb3b" (comb2 #{1 2 3}))


;  (let [n (count coll)
;        x (apply * (repeat n 2))
;        cc (range 0 x)]
;    (for [c cc] (filter #(bit-test (first %) c) (map vector (range) coll)))))

(println "f85a" (f85 #{1 2 3}))
(println "f85b" (f85 #{1 :a}))

(println (= (f85 #{1 :a}) #{#{1 :a} #{:a} #{} #{1}}))
(println (= (f85 #{}) #{#{}}))
(println (= (f85 #{1 2 3})
    #{#{} #{1} #{2} #{3} #{1 2} #{1 3} #{2 3} #{1 2 3}}))
(println (= (count (f85 (into #{} (range 10)))) 1024))


(println "115")

(def f115
  (fn [n]
    (let [digs (map #(- (int %) (int \0)) (seq (str n)))
          i (quot (count digs) 2)
          d (rem (count digs) 2)
          digs1 (take i digs)
          digs2 (drop (+ i d) digs)]
      (= (reduce + digs1) (reduce + digs2)))))

(println (f115 89089))
(println "123" (f115 123))


(println (= true (f115 11)))
(println (= true (f115 121)))
(println(= false (f115 123)))
(println(= true (f115 0)))
(println(= false (f115 88099)))
(println(= true (f115 89098)))
(println(= true (f115 89089)))
;(println(= (take 20 (filter f115 (range)))
;  [0 1 2 3 4 5 6 7 8 9 11 22 33 44 55 66 77 88 99 101]))


(println "105")


(def f105
  (fn [coll]
    (into {}
      (reduce
        (fn [a b]
          (if (keyword? b)
            (conj a [b []])
            (let [xs (butlast a) x (last a)]
              (vec (conj xs [(first x) (conj (second x) b)] )))))
        []
        coll))))


(println (f105 [:a 1 2 3 :b :c 4]))

(println (= {} (f105 [])))
(println (= {:a [1]} (f105 [:a 1])))
(println (= {:a [1], :b [2]} (f105 [:a 1, :b 2])))
(println (= {:a [1 2 3], :b [], :c [4]} (f105 [:a 1 2 3 :b :c 4])))










