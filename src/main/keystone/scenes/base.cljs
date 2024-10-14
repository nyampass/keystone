(ns keystone.scenes.base
  (:require [integrant.core :as ig]
            [shadow.cljs.modern :refer [defclass]]
            ["phaser" :as phaser]))

(defrecord Map [map])

(defprotocol MapBehavior
  (add-tileset-image! [this tileset-name])
  (create-layer! [this layer-name tilesets scale])
  (objects [this]))

(extend-type Map
  MapBehavior
  (add-tileset-image! [this tileset-name]
    (.addTilesetImage (:map this) (name tileset-name)))
  (create-layer! [this layer-name tilesets scale]
    (.setScale
     (.createLayer (:map this) (name layer-name) (clj->js tilesets))
     scale))
  (objects [this]
    (.-objects (:map this))))

(defclass Base
  (extends phaser/Scene)

  (constructor
   [this opts]
   (super opts))

  Object
  (disable-cursor
   [this]
   (set! (-> this .-game .-canvas .-style .-cursor) "none"))

  (load-tilemap [this name path]
                (.tilemapTiledJSON (.-load this) name path))

  (load-image [this name path]
              (.image (.-load this) name path))

  (load-spritesheet [this name path size]
                    (let [[width height] size]
                      (.spritesheet (.-load this)
                                    name path
                                    (js-obj "frameWidth" width "frameHeight" height))))

  (gen-tilemap [this name width height]
               (->Map (.tilemap (.-make this) (js-obj "key" name "tileWidth" width "tileHeight" height))))


  (static-physics-sprite! [this name x y gid scale]
                          (.setScale (.staticSprite (-> this .-physics .-add) x y name gird) scale)))


