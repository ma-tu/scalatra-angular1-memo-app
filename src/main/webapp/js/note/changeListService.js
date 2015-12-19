appModule.factory('changeListService',['$interval','httpService',function($interval,httpService){
  var changeList = {};

  var savingInterval;

  var startAutoSave = function() {
    savingInterval = $interval(function() {
      saveChangeList();
    }.bind(this),1500);
  };

  var stopAutoSave = function() {
    $interval.cancel(savingInterval);
  };

  var saveChangeList = function() {
    if(httpService.getConnectEnable() == false){
      stopAutoSave();
      return;
    }

    if (count() > 0){
      var param = getChangeListParams();
      changeList = {};

      httpService.saveChangeList(param).catch(
        function() {
          stopAutoSave();
        }
      );
    }
  };

  var getChangeListParams = function() {
    var params = [];
    angular.forEach(changeList, function(item) {
      this.push(changeList[item.id]);
    }, params);
    return params;
  };

  var count = function() {
    return Object.keys(changeList).length;
  };

  return {
    initialize : function() {
      changeList = {};
      startAutoSave();
    },

    addNote : function(newId, note) {
      var changeNote = angular.copy(note);
      changeNote.action = "_new";
      changeList[newId] = changeNote;
    },

    updNote : function(id, note) {
      var updNote = angular.copy(note);
      var storedNote = changeList[id];
      if (storedNote == undefined){
        updNote.action = "_upd";
      }else{
        //if action == "_new" then updNote.action == "_new"
        updNote.action = storedNote.action;
      }
      changeList[id] = updNote;
    },

    delNote : function(id, note) {
      var delNote = angular.copy(note);
      var storedNote = changeList[id];
      if (storedNote == undefined){
        delNote.action = "_del";
        changeList[id] = delNote;
      }else if (storedNote.action == "_new"){
        //if delNote == "_new" then only delete
        delete changeList[id];
      }else{
        //if delNote == "_upd" then delNote = "_del"
        delNote.action = "_del";
        changeList[id] = delNote;
      }
    },

    getChangeListStatus : function() {
      var unsavedCount = count();
      if (unsavedCount > 0){
        return "unsaved note:" + unsavedCount;
      }else{
        return "Ready."
      }
    }
  };
}]);
