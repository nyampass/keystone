(ns frameworks.phaser.character
  (:require ["phaser" :as phaser]
            [frameworks.phaser.debug :as debug]))

(defn setup-animations [anims key]
  (doseq [[start end action-name]
          [[0, 20, "idle-down"],
           [21, 41, "idle-left"],
           [42, 62, "idle-up"],
           [66, 79, "walk-down"],
           [80, 93, "walk-left"],
           [94, 107, "walk-up"]]]
    (.create anims (clj->js {:key action-name
                             :frames (.generateFrameNumbers anims key (clj->js {:start start :end end}))
                             :frameRate 36
                             :repeat -1}))))

(defn flip-x! [sprite flip-x?]
  (set! (.-flipX sprite) flip-x?))

(defn dynamic-body [sprite]
  (.-body sprite))

(defn reset-velocity! [sprite]
  (.setVelocity (.-body sprite) 0))

(defn add-x [sprite speed]
  (let [velocity (-> sprite .-body .-velocity)]
    (set! (.-x velocity) (+ (.-x velocity) speed))))

(defn add-y [sprite speed]
  (let [velocity (-> sprite .-body .-velocity)]
    (set! (.-y velocity) (+ (.-y velocity) speed))))

;;   get dynamicBody() {
;;     return this.sprite.body as Phaser.Physics.Arcade.Body;
;;   }

;;   resetVelocity() {
;;     this.dynamicBody.setVelocity(0);
;;   }

;;   addX(speed: number) {
;;     this.dynamicBody.velocity.x += speed;
;;   }

;;   addY(speed: number) {
;;     this.dynamicBody.velocity.y += speed;
;;   }



(defn play [sprite name random?]
  (flip-x! sprite (.endsWith name "-right"))
  (.play sprite (clj->js {:key (.replace name #"-right$" "-left")
                          :startFrame (if random? (.floor js/Math (* (.random js/Math) 20)) 0)})
         true))

;; (require '[frameworks.phaser.debug :as debug])
;; (-> @debug/player .-body)


(defn follow-camera [scene sprite]
  (.startFollow (-> scene .-cameras .-main) sprite true))

(defn idle [sprite]
  (let [current-anim-key (-> sprite .-anims .-currentAnim .-key)]
    (condp = current-anim-key
      "walk-up" (.play sprite "idle-up")
      "walk-down" (.play sprite "idle-down")
      "walk-left" (play sprite (if (.-flipX sprite) "idle-right" "idle-left") false))))


;;   idle() {
;;     const currentKey = this.sprite.anims?.currentAnim?.key;
;;     switch (currentKey) {
;;       case "walk-up":
;;         this.play("idle-up");
;;         break;

;;       case "walk-left":
;;         this.play(!this.sprite.flipX ? "idle-left" : "idle-right");
;;         break;

;;       case "walk-down":
;;         this.play("idle-down");
;;         break;
;;     }
;;   }


(defn gen-character [& {:keys [scene key x y dynamic?]}]
  (prn :gen-char scene key x y dynamic?)
  (let [sprite (if dynamic?
                 (.sprite (-> scene .-physics .-add) x y key)
                 (.staticSprite (-> scene .-physics .-add) x y key))]
    (setup-animations (.-anims sprite) key)
    (if dynamic?
      (do (.setCollideWorldBounds sprite true)
          (.setCircle sprite 25, 65, 90))
      (.setSize (.-body sprite) 50, 100, 40))
    (play sprite "idle-down" (not dynamic?))
    sprite))

;;   collider(target: Character, fn: () => void) {
;;     this.scene.physics.add.collider(this.sprite, target.sprite, fn);
;;   }

;;   colliderLayer(scene: Scene, layer: Phaser.Tilemaps.TilemapLayer) {
;;     scene.physics.add.collider(this.sprite, layer);
;;   }

;;   private flipX(isFlipX: boolean) {
;;     this.sprite.flipX = isFlipX;
;;   }

;;   update() {}
;; }
;; ;; 