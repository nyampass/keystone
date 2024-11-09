(ns frameworks.phaser.character
  (:require ["phaser" :as phaser]))

(def animations phaser/Animations)
(def scene phaser/Scene)

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


;; (defn play [name random?]
;;   )
;;   play(name: string, isRandom: boolean = false) {
;;     this.flipX(name.endsWith("-right"));
;;     this.sprite.play(
;;       {
;;         key: name.replace(/-right$/, "-left"),
;;         startFrame: isRandom ? Math.floor(Math.random() * 20) : 0,
;;       },
;;       true
;;     );
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
    (.play sprite "idle-down" (not dynamic?)))
  sprite)

;; export class Character {
;;   private scene: Scene;
;;   public sprite:
;;     | Phaser.Types.Physics.Arcade.SpriteWithDynamicBody
;;     | Phaser.Types.Physics.Arcade.SpriteWithStaticBody;

;;   constructor(
;;     scene: Phaser.Scene,
;;     key: string,
;;     x: number,
;;     y: number,
;;     isDynamic: boolean
;;   ) {
;;     this.scene = scene;
;;     this.sprite = isDynamic
;;       ? scene.physics.add.sprite(x, y, key)
;;       : scene.physics.add.staticSprite(x, y, key);

;;     this.setupAnimations(this.sprite.anims, key);

;;     if (isDynamic) {
;;       this.sprite.setCollideWorldBounds(true);
;;       this.sprite.setCircle(25, 65, 90);
;;     } else {
;;       (this.sprite.body as any).setSize(50, 100, 40);
;;     }
;;     this.play("idle-down", !isDynamic);
;;   }



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


;;   followCamera() {
;;     this.scene.cameras.main.startFollow(this.sprite, true);
;;   }

;;   collider(target: Character, fn: () => void) {
;;     this.scene.physics.add.collider(this.sprite, target.sprite, fn);
;;   }

;;   colliderLayer(scene: Scene, layer: Phaser.Tilemaps.TilemapLayer) {
;;     scene.physics.add.collider(this.sprite, layer);
;;   }

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

;;   private flipX(isFlipX: boolean) {
;;     this.sprite.flipX = isFlipX;
;;   }

;;   update() {}
;; }
;; ;; 