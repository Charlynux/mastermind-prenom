(ns mastermind
  (:require [clojure.set :as set]
            [clojure.java.io :as io]
            [clojure.xml :as xml]
            [mastermind.logic :as logic]
            [clojure.core.logic :refer [run* membero all and*]]))

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

(defn string->ints [s] (map int s))
(defn ints->string [xs] (apply str (map char xs)))

(comment
  (ints->string (string->ints "ABCDE")))

(defn result->rule [solution {:keys [prenom malPlaces bienPlaces]
                              :or {malPlaces 0, bienPlaces 0}}]
  (let [guess (string->ints prenom)]
    (all
     (logic/count-correct-places solution guess bienPlaces)
     (logic/count-wrong-places solution guess malPlaces))))

(let [prenoms (parse-prenoms (read-prenoms))
      resultats (parse-results (read-results))
      solutions (run* [prenom]
                  (membero prenom (map string->ints prenoms))

                  (and*
                   (map #(result->rule prenom %) resultats)))]
  (set (map ints->string solutions)))
