(ns phone-cities.prod
  (:require
    [phone-cities.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
