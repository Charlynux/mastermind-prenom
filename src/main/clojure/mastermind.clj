(ns mastermind
  (:require [clojure.set :as set]
            [clojure.java.io :as io]
            [clojure.xml :as xml]))

(defn calculate-common-letters [a-word b-word]
  (count (set/intersection (set a-word) (set b-word))))

(comment
  (calculate-common-letters "AAA" "BBB")
  (calculate-common-letters "AAA" "AAA"))

(defn calculate-same-letters [a-word b-word]
  (->> (map = a-word b-word)
       (filter identity)
       (count)))

(comment
  (calculate-same-letters "AAA" "BBB")
  (calculate-same-letters "AAA" "AAA"))

(defn read-results []
  (-> "resultats.xml" io/resource io/file xml/parse :content))

(defn parse-results [results]
  (let [content->tags (map (juxt (comp keyword :tag) (comp first :content)))
        node->result (fn [node] (->> node :content (into {} content->tags)))]
    (map
     (comp #(update % :malPlaces read-string) node->result)
     results)))

(defn read-prenoms []
  (-> "listePrenoms.xml" io/resource io/file xml/parse :content))

(defn parse-prenoms [prenoms]
  (map
   (comp clojure.string/upper-case first :content)
   prenoms))

(defn match? [a-word {:keys [prenom malPlaces bienPlaces]
                      :or {malPlaces 0, bienPlaces 0}}]
  (and (= malPlaces (calculate-common-letters a-word prenom))
       (= bienPlaces (calculate-same-letters a-word prenom))))

(let [prenoms (parse-prenoms (read-prenoms))
      resultats (parse-results (read-results))]
  (reduce
   (fn [prenoms resultat] (filter #(match? % resultat) prenoms))
   prenoms
   resultats))
