'use strict';

angular.module('zetravelcloudApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('city', {
                parent: 'entity',
                url: '/citys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.city.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/city/citys.html',
                        controller: 'CityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('city');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('city.detail', {
                parent: 'entity',
                url: '/city/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.city.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/city/city-detail.html',
                        controller: 'CityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('city');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'City', function($stateParams, City) {
                        return City.get({id : $stateParams.id});
                    }]
                }
            })
            .state('city.new', {
                parent: 'city',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/city/city-dialog.html',
                        controller: 'CityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('city', null, { reload: true });
                    }, function() {
                        $state.go('city');
                    })
                }]
            })
            .state('city.edit', {
                parent: 'city',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/city/city-dialog.html',
                        controller: 'CityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['City', function(City) {
                                return City.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('city', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('city.delete', {
                parent: 'city',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/city/city-delete-dialog.html',
                        controller: 'CityDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['City', function(City) {
                                return City.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('city', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
