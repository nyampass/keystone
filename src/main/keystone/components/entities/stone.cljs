(ns keystone.components.entities.stone)

(defn x [])

(defn gen-stone [id sprite]
  {:id id :sprite sprite})


;; export class Stone {
;;   id: number;
;;   sprite: Phaser.Types.Physics.Arcade.SpriteWithStaticBody;
;;   param: Parameter;

;;   constructor(id: number, scene: Scene, x: number, y: number, gid: number) {
;;     this.id = id;
;;     this.sprite = scene.physics.add
;;       .staticSprite(x, y, "items", gid)
;;       .setScale(1.5);
;;     this.param = {dir:0}
;;   }
;; }

;; (def generate [scene objects]
;;   (map (fn [{:keys [id x y gid]}]
;;          (prn [id x y gid])
;;          (gen-stone id scene (* x 1.5) (* y 1.5) (- gid 1261))
;;        objects)))