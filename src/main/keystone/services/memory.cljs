(ns keystone.infrastructure.repository.memory
  (:require [keystone.application.repository :as interface]))

(defrecord MemoryRepo [state]
  interface/Repository
  (save-storylines [this])
  (load-storylines [this]))