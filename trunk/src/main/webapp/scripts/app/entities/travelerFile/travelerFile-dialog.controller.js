'use strict';

angular.module('zetravelcloudApp').controller('TravelerFileDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'TravelerFile', 'Traveler',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, TravelerFile, Traveler) {

        $scope.travelerFile = entity;
        $scope.travelers = Traveler.query();
        $scope.load = function(id) {
            TravelerFile.get({id : id}, function(result) {
                $scope.travelerFile = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('zetravelcloudApp:travelerFileUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.travelerFile.id != null) {
                TravelerFile.update($scope.travelerFile, onSaveSuccess, onSaveError);
            } else {
                TravelerFile.save($scope.travelerFile, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setFile = function ($file, travelerFile) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        travelerFile.file = base64Data;
                        travelerFile.fileContentType = $file.type;
                    });
                };
            }
        };
}]);
