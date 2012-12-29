(ns plosure.file.read
  (:use [clojure.java.io]))

(defn print-with-line-numbers [path]
  (with-open [rdr (reader path)]
    (let [lines (line-seq rdr)
          no-lines (map #(str %1 ": " %2) (range) lines)]
      (doseq [line no-lines]
        (println line)))))

(defn print-remove-comments [path]
  (with-open [rdr (reader path)]
    (let [lines (line-seq rdr)
          no-comm-lines (filter #(not (.startsWith % "#")) lines)]
      (doseq [line no-comm-lines]
        (println line)))))

(defn -main [path]
  ;(print-with-line-numbers path)
  (print-remove-comments path)
  )