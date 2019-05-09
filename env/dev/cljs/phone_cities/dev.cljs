(ns ^:figwheel-no-load phone-cities.dev
  (:require
    [phone-cities.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
