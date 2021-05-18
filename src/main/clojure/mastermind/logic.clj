(ns mastermind.logic
  ;; TODO : EXCLUDE ==
  (:require
   [clojure.core.logic :refer :all]
   [clojure.core.logic.fd :as fd]))

(defn count-correct-places [solution guess n]
  (matche [solution guess n]
          ([[] [] 0])
          ([[a . s-tail] [a . g-tail] _]
           (fresh [n-1]
             (fd/+ n-1 1 n)
             (count-correct-places s-tail g-tail n-1)))
          ([[a . s-tail] [b . g-tail] _]
           (!= a b)
           (count-correct-places s-tail g-tail n))))
