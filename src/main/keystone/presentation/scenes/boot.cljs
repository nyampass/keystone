(ns keystone.presentation.scenes.boot
  (:require [integrant.core :as ig]
            [shadow.cljs.modern :refer [defclass]]
            ["phaser" :as phaser]))

(defclass XX
  (extends phaser/Scene)
  (field a-b)
  (constructor [this opts]
               (prn :cons opts)
               (set! (.-a-b this) (:v opts)))

  Object
  (sum [this] [a-b]))


;; (XX. {:a 3})

;; (.sum (XX. {:v 3}))

;; (def x (B. 4 4))
;; (.constructor x 1 2)

;; (set! (.-a x) 3)
;; (.-a  x)

(defclass Boot
  (extends phaser/Scene)

  (field next-scene-key)

  (constructor [this opts]
               (super #js {:key "boot"})
               (set! (.-next-scene-key this) (:next opts)))

  Object
  (preload [this]
           (prn :preload)
           ;;  TODO load assets
           (let [loader (.-load this)]
             (.image loader "hoge")))
  (create [this]
          (.start (.-scene this) "dev")))

(defmethod ig/init-key :presentation.scenes/boot [_ {:keys [next]}]
  (Boot. {:next next}))


;;     loader.tilemapTiledJSON("episode1", "/assets/map/episode1.json");
;;     loader.image(
;;       "the_japan_collection_overgrown_backstreets",
;;       "/assets/spritesheet/the_japan_collection_overgrown_backstreets.png",
;;     );
;;     loader.image("spritesheet_32x32", "/assets/spritesheet/spritesheet_32x32.png");
;;     loader.spritesheet("items", "/assets/spritesheet/spritesheet_32x32.png", {
;;       frameWidth: 32,
;;       frameHeight: 32,
;;     });
;;     loader.spritesheet("nerd", "/assets/spritesheet/nerd.png", {
;;       frameWidth: 180,
;;       frameHeight: 150,
;;     });
;;     loader.spritesheet("player", "/assets/spritesheet/player.png", {
;;       frameWidth: 180,
;;       frameHeight: 150,
;;     });
;;   }
