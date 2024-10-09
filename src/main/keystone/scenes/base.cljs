(ns keystone.scenes.base
  (:require [integrant.core :as ig]
            [shadow.cljs.modern :refer [defclass]]
            ["phaser" :as phaser]))


(defrecord Map [map])

(defprotocol MapBehavior
  (add-tileset-image! [this tileset-name])
  (create-layer! [this layer-name tilesets]))

(extend-type Map
  MapBehavior
  (add-tileset-image! [this tileset-name]
    (prn :name name)
    (.addTilesetImage (:map this) (name tileset-name)))
  (create-layer! [this layer-name tilesets]
    (prn :hoge3333 (.-layers (:map this)))
    (.createLayer (:map this) (name layer-name) tilesets)))

(defclass Base
  (extends phaser/Scene)

  (constructor
   [this opts]
   (super opts))

  Object
  (disable-cursor
   [this]
   (set! (-> this .-game .-canvas .-style .-cursor) "none"))

  (load-tilemap [this path]
                (.tilemapTiledJSON (.-load this) path))

  (load-image [this name path]
              (.image (.-load this) name path))

  (load-spritesheet [this name path size]
                    (let [[width height] size]
                      (prn 3)
                      ;; (.spritesheet (.-load this)
                      ;;               name path
                      ;;               (js-obj "frameWidth" width "frameHeight" height))
                      ))

  (gen-tilemap [this name width height]
               (->Map (.tilemap (.-make this) (js-obj :key name :tileWidth width :tileHeight height)))))

