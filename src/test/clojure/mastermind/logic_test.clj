(ns mastermind.logic-test
  (:require [clojure.test :refer [deftest is]]
            [clojure.core.logic :refer [run*]]
            [mastermind.logic :refer [count-correct-places]]))

(deftest count-empty-solution
  (is (= (list 0)
         (run* [n]
           (count-correct-places [] [] n)))))

(deftest count-full-solution-1
  (is (= (list 1)
         (run* [n]
           (count-correct-places [1] [1] n)))))

(deftest count-full-solution-5
  (is (= (list 5)
         (run* [n]
           (count-correct-places [1 2 3 4 5] [1 2 3 4 5] n)))))

(deftest count-full-wrong
  (is (= (list 0)
         (run* [n]
           (count-correct-places [1 1 1] [0 0 0] n)))))

(deftest count-half-right
  (is (= (list 2)
         (run* [n]
           (count-correct-places [1 1 1 1] [1 1 0 0] n)))))

(deftest find-solution-from-correct-places
  (is (= (list [0 1 2 3])
         (run* [solution]
           (count-correct-places solution [0 1 2 3] 4)))))
