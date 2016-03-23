'use strict';

angular.module('zetravelcloudApp')
    .factory('ProposedRoom', function ($resource, DateUtils) {
        return $resource('api/proposedRooms/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
