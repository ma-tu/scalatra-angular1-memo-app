appModule.factory('noteOperatorService', ['$q', '$interval', 'httpService', 'noteModelService', 'changeListService', function($q, $interval, httpService, noteModelService, changeListService){
  return {
    loadNoteList : function () {
      var deferred = $q.defer();
      httpService.loadNoteList().then(
        function(notes) {
          noteModelService.setNoteList(notes);
          noteModelService.setNotSelected();
          changeListService.initialize();
          deferred.resolve()
        },function() {
          deferred.reject()
        }
      );
      return deferred.promise;
    },

    addNote : function (newTitle) {
      var deferred = $q.defer();
      httpService.getNewNoteId().then(
        function(newId) {
          var addNote = noteModelService.createAddNote(newId, newTitle);
          noteModelService.addNote(addNote);
          changeListService.addNote(newId,addNote);
          deferred.resolve();
        },function() {
          deferred.reject();
        }
      );
      return deferred.promise;
    },

    updateNoteText : function (noteText) {
      if (noteModelService.hasSelectedNote()){
        noteModelService.updateSelectedNoteText(noteText);
        changeListService.updNote(noteModelService.getSelectedNoteId(), noteModelService.getSelectedNote());
      }
    },

    updateNoteTitle : function(noteTitle) {
      if (noteModelService.hasSelectedNote()){
        noteModelService.updateSelectedNoteTitle(noteTitle);
        changeListService.updNote(noteModelService.getSelectedNoteId(), noteModelService.getSelectedNote());
      }
    },

    deleteNote : function() {
      var deleteNote = noteModelService.getSelectedNote();
      if (deleteNote != null){
        noteModelService.deleteSelectedNote();
        changeListService.delNote(deleteNote.id, deleteNote);
      }
    },

    getSavingStatus : function () {
      if (httpService.getConnectMessage() != ""){
        return httpService.getConnectMessage();
      }else{
        return changeListService.getChangeListStatus();
      }
    }
  };
}]);