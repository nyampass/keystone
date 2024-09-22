(ns keystone.presentation.scenes.boot
  (:require [integrant.core :as ig]
            [cljs.spec.alpha :as s]
            [shadow.cljs.modern :refer [defclass]]
            [keystone.presentation.scenes.base :refer [Base]]))

(s/def ::tilemaps (s/coll-of keyword?))

(s/def ::images (s/coll-of keyword?))

(s/def :spritesheet/name string?)
(s/def ::spritesheets (s/map-of keyword? (s/keys :opt-un [:spritesheet/name])))

(s/def ::assets (s/keys :req-un [(or ::tilemaps ::images ::spritesheets)]))

(defclass Boot
  (extends Base)

  (field assets)
  (field next-scene-key)

  (constructor [this opts]
               (super #js {:key "boot"})
               (set! (.-next-scene-key this) (name (:next opts)))
               (set! (.-assets this) (:assets opts)))

  Object
  (preload [this]
           (let [{:keys [tilemaps images spritesheet]} assets]
             (prn [tilemaps images spritesheet])
             (doseq [tilemap tilemaps]
               (.load-tilemap this tilemap))))
  (create [this]
          (.start (.-scene this) (.-next-scene-key this))))

(defmethod ig/assert-key :presentation.scenes/boot [_ {:keys [assets]}]
  (s/assert ::assets assets))

(defmethod ig/init-key :presentation.scenes/boot [_ {:keys [next assets]}]
  (Boot. {:next next :assets assets}))


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
