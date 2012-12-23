(ns plosure.miasto.transform)

(def data1 [[:enter :A]
            [:enter :AB]
            [:enter :ABC]
            [:exit]
            [:exit]
            [:enter :AD]
            [:exit]
            [:exit]])

(def result1 [:A
              [:AB
               [:ABC nil nil]
               nil]
              [:AD nil nil]])

(def data2 [[:enter :ROOT]
            [:enter :L1]
            [:enter :L2]
            [:enter :L3]
            [:exit]
            [:enter :R3]
            [:exit]
            [:exit]
            [:enter :R2]
            [:exit]
            [:exit]
            [:enter :R1]
            [:exit]
            [:exit]])

(def result2 [:ROOT
              [:L1
               [:L2
                [:L3 nil nil]
                [:R3 nil nil]]
               [:R2 nil nil]]
              [:R1 nil nil]] )


(defn left-tree-size [v]
  (let [ones (map #(if (= (first %) :enter) +1 -1) v)]
    (inc (.indexOf (vec (map #(apply + (take % ones)) (range 1 (count ones)))) 0))))

(defn tree [v]
  "converts code list representation into tree representation"
  (let [cmd (second (first v))
        n (left-tree-size (rest v))
        vl (take n (rest v))
        vr (drop n (rest v))]
    (if cmd [cmd (tree vl) (tree vr)] nil)))

(defn -main[]
  (do
    (println "test1: " (= (tree data2) result2))
    (println "test2: " (= (tree data1) result1))
    (println)
    (println data1 "\n==>\n" (tree data1))
    (println)
    (println data2 "\n==>\n" (tree data2)))
    (println))


