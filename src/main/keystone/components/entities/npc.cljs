(ns keystone.components.entities.npc
  (:require [keystone.frameworks.phaser.character :refer [gen-character]]))

;; import { Scene } from "phaser";
;; import { Character } from "./character";

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
  (do (map #(gen-character scene "nerd" 300 300) (range 3))))