(ns keystone.infrastructure.repository
  (:require [integrant.core :as ig]
            [keystone.infrastructure.repository.memory :as memory]))

(defmethod ig/init-key :infrastructure.repository/memory [_ opts])