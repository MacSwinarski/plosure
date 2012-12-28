(ns plosure.forclojure.beautysymetry)

(def bs1?
  (fn sym?
    ([t] (sym? (first (rest t)) (second (rest t))))
    ([t1 t2]
      (or
        (and (nil? t1) (nil? t2))
        (and
          (= (first t1) (first t2))
          (sym? (first (rest t1)) (second (rest t2)))
          (sym? (second (rest t1)) (first (rest t2))))))))

(def bs2?
  (fn sym?
    ([[v l r]] (sym? l r))
    ([[v1 l1 r1]  [v2 l2 r2]]
      (or
        (and (nil? v1) (nil? v2))
        (and (= v1 v2) (sym? l1 r2) (sym? r1 l2))))))

(def maximental #(= % ((fn m [[v l r]] (if v [v (m r) (m l)])) %)))


;(def bs? bs1?)
;(def bs? bs2?)
(def bs? maximental)

(defn -main[]
  (do
    (println "1:" (= (bs? '(:a (:b nil nil) (:b nil nil))) true))
    (println "2:" (= (bs? '(:a (:b nil nil) nil)) false))
    (println "3:" (= (bs? '(:a (:b nil nil) (:c nil nil))) false))
    (println "4:" (= (bs? [1
                           [2 nil [3     [4 [5 nil nil] [6 nil nil]] nil]    ]
                           [2     [3 nil [4 [6 nil nil] [5 nil nil]]    ] nil]
                           ])
                    true))
    (println "5:" (= (bs? [1
                           [2 nil [3     [4 [5 nil nil] [6 nil nil]] nil]    ]
                           [2     [3 nil [4 [5 nil nil] [6 nil nil]]    ] nil]
                           ])
            false))
    (println "6:" (= (bs? [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
            [2 [3 nil [4 [6 nil nil] nil]] nil]])
            false))))



