(ns plosure.mail.emails
  (:use [clojure.java.io]))

(defn emails []
  (with-open [rdr (reader "/Users/mac/dev/plosure/resources/emails.txt")]
    (doall (line-seq rdr))))

(defn -main []
  (println (count (emails))))