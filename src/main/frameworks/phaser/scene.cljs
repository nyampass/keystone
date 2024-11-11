(ns frameworks.phaser.scene
  (:require [shadow.cljs.modern :refer [defclass]]
            ["phaser" :as phaser]))

(defrecord Map [map])

(defprotocol MapBehavior
  (add-tileset-image! [this tileset-name])
  (create-layer! [this layer-name tilesets scale])
  (object-props [this index]))

(extend-type Map
  MapBehavior
  (add-tileset-image! [this tileset-name]
    (.addTilesetImage (:map this) (name tileset-name)))
  (create-layer! [this layer-name tilesets scale]
    (.setScale
     (.createLayer (:map this) (name layer-name) (clj->js tilesets))
     scale))
  (object-props [this index]
    (letfn [(retrive-prop [object-layer]
              {:x (.-x object-layer)
               :y (.-y object-layer)
               :gid (.-gid object-layer)})]
      (map retrive-prop (-> this
                            :map
                            .-objects
                            (aget index)
                            .-objects)))))

(defn load-tilemap [scene name path]
  (prn :load-tilemap scene name path)
  (.tilemapTiledJSON (.-load scene) name path))

(defn load-image [scene name path]
  (.image (.-load scene) name path))

(defn load-spritesheet [scene name path size]
  (let [[width height] size]
    (.spritesheet (.-load scene)
                  name path
                  (js-obj "frameWidth" width "frameHeight" height))))

(defn gen-tilemap [scene name width height]
  (->Map (.tilemap (.-make scene) (js-obj "key" name "tileWidth" width "tileHeight" height))))

(defn static-physics-sprite! [scene name x y gid scale]
  (.setScale (.staticSprite (-> scene .-physics .-add) x y name gid) scale))

(defn disable-cursor [scene]
  (set! (-> scene .-game .-canvas .-style .-cursor) "none"))

(defn start-scene! [scene next]
  (.start (.-scene scene) (name next)))

(def key-code-map {:arrowleft :arrow-left})

(defn- key->key-code [key]
  (let [key-code (-> key .-code .toLowerCase keyword)]
    (get key-code-map key-code key-code)))

(defn- handle-key-down [scene {:keys [key-down]} key]
  (prn :key (-> key .-code .toLowerCase))
  (when key-down
    (when-let [key-code (key->key-code key)]
      (key-down scene key-code))))

(defn- handle-key-up [scene {:keys [key-up]} key]
  (when key-up
    (when-let [key-code (key->key-code key)]
      (key-up scene key-code))))

(defclass BaseScene
  (extends phaser/Scene)
  (field callbacks)

  (constructor
   [this scene-name callbacks]
   (super (clj->js {:key scene-name}))
   (set! (.-callbacks this) callbacks)
   (when-let [init (:init callbacks)]
     (init this)))

  Object
  (preload [this]
           (prn :preload (.-this name) callbacks)
           (when-let [preload (:preload callbacks)]
             (preload this)))

  (create [this]
          (.on (-> this .-input .-keyboard)
               "keydown" (fn [key] (handle-key-down this callbacks key)))
          (.on (-> this .-input .-keyboard)
               "keyup" (fn [key] (handle-key-up this callbacks key)))

          (when-let [created (:created callbacks)]
            (created this))))


(defn gen-scene [scene-name callbacks]
  (BaseScene. scene-name callbacks))