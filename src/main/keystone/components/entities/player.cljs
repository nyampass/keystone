(ns keystone.components.entities.player)

;; import { Scene } from "phaser";
;; import { Character } from "./character";
;; import { Direction } from "../../infra/adapter/input";

;; const direction2playName = new Map<Direction, string>([
;;   [Direction.right, "walk-right"],
;;   [Direction.left, "walk-left"],
;;   [Direction.up, "walk-up"],
;;   [Direction.down, "walk-down"],
;; ]);

;; export class Player extends Character {
;;   direction?: Direction;

;;   moveSpeed: number = 320;

;;   constructor(scene: Scene) {
;;     super(
;;       scene,
;;       "player",
;;       scene.sys.game.canvas.width / 2,
;;       scene.sys.game.canvas.height / 2,
;;       true
;;     );

;;     this.followCamera();
;;   }

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
