(ns keystone.scenes.boot
  (:require [integrant.core :as ig]
            [cljs.spec.alpha :as s]
            [frameworks.phaser.scene :as sc]))

(s/def ::tilemaps (s/coll-of keyword?))

(s/def ::images (s/coll-of keyword?))

(s/def :spritesheet/name string?)
(s/def :spritesheet/size (s/coll-of number? :count 2))
(s/def ::spritesheets (s/map-of keyword? (s/keys :req-un [:spritesheet/size] :opt-un [:spritesheet/name])))

(s/def ::assets (s/keys :req-un [(or ::tilemaps ::images ::spritesheets)]))

(defmethod ig/assert-key :scenes/boot [_ {:keys [assets]}]
  (s/assert ::assets assets))

(defn gen-boot-scene [{:keys [next assets]}]
  (sc/gen-scene
   :boot
   {:init (fn [_this] (prn "start boot"))
    :preload (fn [this]
               (prn "preload boot")
               (let [{:keys [tilemaps images spritesheets]} assets]
                 (prn :preload tilemaps)
                 (doseq [tilemap tilemaps]
                   (sc/load-tilemap this (name tilemap) (str "tilemaps/" (name tilemap) ".json")))
                 (doseq [image images]
                   (sc/load-image this (name image) (str "images/" (name image) ".png")))
                 (doseq [[key opts] spritesheets]
                   (let [path (or (:name opts) (str (name key) ".png"))]
                     (sc/load-spritesheet this (name key) (str "spritesheets/" path) (:size opts))))))
    :created (fn [this]
               (prn "created boot")
               (sc/start-scene! this next))}))

(defmethod ig/init-key :scenes/boot [_ opts]
  (gen-boot-scene opts))

