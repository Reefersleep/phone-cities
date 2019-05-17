(ns phone-cities.subs
  (:require [re-frame.core]))

(defn score-for-one-color
  [cards]
  (let [selected-cards             (->> cards
                                        (filter :selected?))
        number-of-handshake-cards  (->> selected-cards
                                        (filter (comp #{:handshake-1
                                                        :handshake-2
                                                        :handshake-3}
                                                      :card-identity))
                                        count)
        accummulated-number-values (->> selected-cards
                                        (remove (comp #{:handshake-1
                                                        :handshake-2
                                                        :handshake-3}
                                                      :card-identity))
                                        (map :card-identity)
                                        (apply +))
        more-than-8-cards-bonus    (when (<= 8 (count selected-cards))
                                     20)]
    (if (seq selected-cards)
      (-> -20
          (+ accummulated-number-values)
          (* (inc number-of-handshake-cards))
          (+ more-than-8-cards-bonus))
      0)))

(re-frame.core/reg-sub
 :cards-for-player
 (fn [db [_ player]]
   (->> db
        :cards
        player)))

(re-frame.core/reg-sub
 :current-cards
 (fn [{:keys [currently-editing-cards-for-player] :as db} _]
   (->> db
        :cards
        currently-editing-cards-for-player)))

(re-frame.core/reg-sub
 :currently-editing-cards-for-player
 (fn [db _]
   (->> db
        :currently-editing-cards-for-player)))

(re-frame.core/reg-sub
 :score-for-player
 (fn [db [_ player]]
   (->> db
        :cards
        player
        vals
        (group-by :color-identity)
        (map (fn [[color-identity cards]]
               (score-for-one-color cards)))
        (apply +))))
