(ns keystone.models.npc
  (:require [frameworks.phaser.character :refer [gen-character]]))

(defn gen-npc [scene key x y]
  (prn :gen-npc)
  (gen-character :scene scene :key key :x x :y y :dynamic? false))

(defn random-npcs [scene]
  (map (fn [_]
         (gen-npc scene "nerd"
                  (* (.random js/Math)
                     (-> scene .-sys .-game .-canvas .-width))
                  (* (.random js/Math)
                     (-> scene .-sys .-game .-canvas .-height)))) (range 30)))