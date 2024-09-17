(ns keystone.system
  (:require [integrant.core :as ig]
            [keystone.adapter.phaser]
            [keystone.presentation.scenes.boot]))

(def config
  {:adapter/phaser {:scenes [(ig/ref :presentation.scenes/boot)]
                    :width 1600 :height 1200}

   :presentation.scenes/boot {}})

(defn init []
  (ig/init config))