(ns phone-cities.events
  (:require [re-frame.core]))

(def color-identities [:yellow :blue :white :green :red :purple])

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
    {:currently-editing-cards-for-player :player-1
     :cards {:player-1 initialized-cards
             :player-2 initialized-cards}
     :colors              color-identities}}))

(re-frame.core/reg-event-db
 :mouse-toggle-card
 (fn [{:keys [currently-editing-cards-for-player] :as db} [_ index]]
   (-> db
       (update-in [:cards currently-editing-cards-for-player index :selected?] not)
       (update-in [:cards :player-1] (partial into (sorted-map-by <)))
       (update-in [:cards :player-2] (partial into (sorted-map-by <))))))

(re-frame.core/reg-event-db
 :set-currently-editing-cards-for-player
 (fn [db [_ player]]
   (-> db
       (assoc :currently-editing-cards-for-player player)
       (update-in [:cards :player-1] (partial into (sorted-map-by <)))
       (update-in [:cards :player-2] (partial into (sorted-map-by <))))))
