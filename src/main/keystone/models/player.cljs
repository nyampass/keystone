(ns keystone.models.player
  (:require [frameworks.phaser.character :as pc :refer [gen-character play follow-camera reset-velocity!
                                                        add-x add-y]]
            [frameworks.phaser.debug :refer [set-player!]]))

(def move-speed 320)

(def direction->play-names {:right "walk-right"
                            :left "walk-left"
                            :up "walk-up"
                            :down "walk-down"})



(defn move [{:keys [state player]} move-key]
  (when-let [anim ({:left "walk-left",
                    :right "walk-right",
                    :up "walk-up",
                    :down "walk-down"} move-key)]
    (play player anim false)
    (swap! state assoc :dir move-key)))

(defn update [{:keys [state player]}]
  (reset-velocity! player)
  (when-let [dir (:dir @state)]
    (condp = dir
      :left (add-x player (- move-speed))
      :right (add-x player move-speed)
      :up (add-y player (- move-speed))
      :down (add-y player move-speed)))
  (prn :update (:dir @state)))

(defn idle [{:keys [state player]}]
  (swap! state assoc :dir nil)
  (pc/idle player))

(defn gen-player [scene]
  (let [state (atom {:dir nil})
        player (gen-character :scene scene :key "player"
                              :x (/ (-> scene .-sys .-game .-canvas .-width) 2)
                              :y (/ (-> scene .-sys .-game .-canvas .-height) 2)
                              :dynamic? true)]
    (follow-camera scene player)
    (set-player! player)
    {:state state :player player}))

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
