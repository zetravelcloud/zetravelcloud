'use strict';

angular.module('zetravelcloudApp').controller('ClientDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Client',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, Client) {

        $scope.client = entity;
        $scope.load = function(id) {
            Client.get({id : id}, function(result) {
                $scope.client = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('zetravelcloudApp:clientUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.client.id != null) {
                Client.update($scope.client, onSaveSuccess, onSaveError);
            } else {
                Client.save($scope.client, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setImage = function ($file, client) {
            if ($file && $file.$error == 'pattern') {
                return;
            }
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        client.image = base64Data;
                        client.imageContentType = $file.type;
                    });
                };
            }
        };
}]);
