(ns phone-cities.events
  (:require [re-frame.core]))

(def color-identities [:yellow :blue :white :green :red])

(def card-values [:handshake-1
                  :handshake-2
                  :handshake-3
                  2
                  3
                  4
                  5
                  6
                  7
                  8
                  9
                  10])

(def initialized-cards
  (->> color-identities
       (mapcat (fn color-identity->stack [color-identity]
                 (->> (range 1 11)
                      (concat [:handshake-1 :handshake-2 :handshake-3])
                      (map (fn [ident]
                             {:card-identity  ident
                              :color-identity color-identity
                              :selected?      false})))))
       (map-indexed (fn add-indices [i card]
                      [i card]))
       (into (sorted-map-by <))))

(re-frame.core/reg-event-fx
 :initialize-db
 (fn [_]
   {:db
    {:cards               initialized-cards
     :colors              color-identities}}))

(re-frame.core/reg-event-db
 :mouse-toggle-card
 (fn [db [_ index]]
   (-> db
       (update-in [:cards index :selected?] not)
       (update-in [:cards] (partial into (sorted-map-by <))))))
