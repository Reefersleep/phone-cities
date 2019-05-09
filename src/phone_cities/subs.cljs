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
 :cards
 (fn [db _]
   (->> db
        :cards)))

(re-frame.core/reg-sub
 :score
 :<- [:cards]
 (fn [cards _]
   (->> cards
        vals
        (group-by :color-identity)
        (map (fn [[color-identity cards]]
               (score-for-one-color cards)))
        (apply +))))

#_(re-frame.core/reg-sub
 :score
 :<- [:cards]
 (fn [cards _]
   (->> cards
        (map (fn [[color-identity cards]]
               (score-for-one-color cards)))
        (apply +))))

#_(re-frame.core/reg-sub
 :score-for-color
 (fn [db [_ color-identity]]
   (let [{:keys [:cards :show-score-details?]} db]
     (when show-score-details?
       (-> db
           :cards
           (get color-identity)
           score-for-one-color)))))
