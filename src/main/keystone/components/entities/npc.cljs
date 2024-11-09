(ns keystone.components.entities.npc
  (:require [frameworks.phaser.character :refer [gen-character]]))

(defn gen-npc [scene key x y]
  (prn :gen-npc)
  (gen-character :scene scene :key key :x x :y y :dynamic? false))

;; export class NPC extends Character {
;;   constructor(scene: Scene, key: string, x: number, y: number) {
;;     super(scene, key, x, y, false);
;;   }
;; }

;; export const randomNPCs = (scene: Scene) =>
;;   [...Array(3)].map(
;;     () =>
;;       new NPC(
;;         scene,
;;         "nerd",
;;         Math.random() * scene.sys.game.canvas.width,
;;         Math.random() * scene.sys.game.canvas.height,
;;       )
;;   );

(defn random-npcs [scene]
  (map (fn [_]
         (gen-npc scene "nerd"
                  (* (.random js/Math)
                     (-> scene .-sys .-game .-canvas .-width))
                  (* (.random js/Math)
                     (-> scene .-sys .-game .-canvas .-height)))) (range 30)))