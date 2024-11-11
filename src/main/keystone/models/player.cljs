(ns keystone.models.player
  (:require [frameworks.phaser.character :as pc]))

(def flip-x! pc/flip-x!)

;; import { Scene } from "phaser";
;; import { Character } from "./character";
;; import { Direction } from "../../infra/adapter/input";

(def direction->play-names {:right "walk-right"
                            :left "walk-left"
                            :up "walk-up"
                            :down "walk-down"})

(defn gen-player [scene]
  (let [player   (pc/gen-character :scene scene :key "player"
                                   :x (/ (-> scene .-sys .-game .-canvas .-width) 2)
                                   :y (/ (-> scene .-sys .-game .-canvas .-height) 2)
                                   :dynamic? true)]
    (pc/follow-camera scene player)
    player))

;; const direction2playName = new Map<Direction, string>([
;;   [Direction.right, "walk-right"],
;;   [Direction.left, "walk-left"],
;;   [Direction.up, "walk-up"],
;;   [Direction.down, "walk-down"],
;; ]);

;; export class Player extends Character {
;;   direction?: Direction;

;;   moveSpeed: number = 320;

;;   idle() {
;;     this.direction = undefined;
;;     super.idle();
;;   }

;;   move(direction: Direction) {
;;     if (this.direction === direction) {
;;       return;
;;     }
;;     this.direction = direction;
;;     this.play(direction2playName.get(direction)!);
;;   }

;;   update() {
;;     this.resetVelocity();
;;     switch (this.direction) {
;;       case Direction.right:
;;         this.addX(this.moveSpeed);
;;         break;

;;       case Direction.down:
;;         this.addY(this.moveSpeed);
;;         break;

;;       case Direction.left:
;;         this.addX(-this.moveSpeed);
;;         break;

;;       case Direction.up:
;;         this.addY(-this.moveSpeed);
;;         break;
;;     }
;;   }
;; }
