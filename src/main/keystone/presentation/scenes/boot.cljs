(ns keystone.presentation.scenes.boot
  (:require [integrant.core :as ig]
            [shadow.cljs.modern :refer [defclass]]
            ["phaser" :as phaser]))

(defclass Boot
  (extends phaser/Scene)

  (field next_scene_key)

  (constructor [this next-scene-key]
               (prn :constructor next-scene-key)
               (set! (.-next-scene-key this) next-scene-key)
               (super #js {:key "boot"}))

  Object
  (preload [this]
           (prn :preload)
           ;;  TODO load assets
           (let [loader (.-load this)]
             (.image loader "hoge")))
  (create [this]
          (.start (.-scene this) "dev")))

(defmethod ig/init-key :presentation.scenes/boot [_ {:keys [next]}]
  (Boot. #js {:next next}))


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
