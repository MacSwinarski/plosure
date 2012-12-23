(ns plosure.miasto.sek)

(def data [1,2,4,4,5,9,6,4,3,5,6,7,100])

(defn cut1[s]
  "creates lazy sequence with ascending elements of sequence s"
  (cons (first s) (lazy-seq
    (if (> (first s) (second s)) [] (cut1 (rest s))))))

(defn cut2[s]
  "creates lazy sequence with ascending elements of sequence s"
  (cons (first s)
        (map second (take-while #(<= (first %) (second %)) (map vector s (rest s))))))

(defn -main[]
  (println (cut1 data) (= (cut1 data) [1,2,4,4,5,9]))
  (println (cut2 data) (= (cut2 data) [1,2,4,4,5,9])))
