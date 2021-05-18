(ns mastermind.logic
  (:refer-clojure :exclude [==])
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

(defne nonmembero
  "A relation where l is a collection, such that l does not contain x"
  [x l]
  ([_ ()])
  ([_ [head . tail]]
   (!= x head)
   (nonmembero x tail)))

(defn count-wrong-places [solution guess n]
  (letfn [(count-wrong-recur [sol guess n]
            (matche [sol guess n]
                    ([[] [] 0])
                    ([[a . s-tail] [b . g-tail] _]
                     (!= a b)
                     (membero b solution)
                     (fresh [n-1]
                       (fd/+ n-1 1 n)
                       (count-wrong-recur s-tail g-tail n-1)))
                    ([[a . s-tail] [b . g-tail] _]
                     (conde
                      [(== a b)]
                      [(nonmembero b solution)])
                     (count-wrong-recur s-tail g-tail n))))]
    (count-wrong-recur solution guess n)))

(comment
  (let [size 4
        domain [1 2 3 4]
        reference (repeatedly size lvar)]
    (run* [solution]
      (== reference solution)
      (everyg #(membero % domain) solution)

      (count-correct-places solution [1 2 3 4] 2)
      (count-wrong-places solution [1 2 3 4] 2)

      (count-correct-places solution [1 3 4 2] 2)
      (count-wrong-places solution [1 3 4 2] 2)))
  )

(run* [q]
  (conde
   [(== q 1)]
   [(== q 1)]))
