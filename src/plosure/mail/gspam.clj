(ns plosure.mail.gspam
  (:use [postal.core]))

(defn s [from password]
  (send-message
  ^{:host "smtp.gmail.com"
    :user from
    :pass password
    :ssl :yes!!!11}
   {:from "motopig@gmail.com"
    :to ["yana.arkhipova@gmail.com" "maciej.swinarski@gmail.com"]
    :subject "Yo ziomus"
    :body [{:type "text/html"
           :content "<html><head></head>
                     <body>
                     <h1>Ho Ho Ho Yana!</h1><p>Ya eto vyslal z clojure project plosure.</p>
                     </body></html>"}]
    }))

(defn -main [from password]
  (do
    (println "GSPAM")
    (s from password)))


