'use strict';

angular.module('zetravelcloudApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('offeredServiceType', {
                parent: 'entity',
                url: '/offeredServiceTypes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.offeredServiceType.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/offeredServiceType/offeredServiceTypes.html',
                        controller: 'OfferedServiceTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('offeredServiceType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('offeredServiceType.detail', {
                parent: 'entity',
                url: '/offeredServiceType/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.offeredServiceType.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/offeredServiceType/offeredServiceType-detail.html',
                        controller: 'OfferedServiceTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('offeredServiceType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'OfferedServiceType', function($stateParams, OfferedServiceType) {
                        return OfferedServiceType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('offeredServiceType.new', {
                parent: 'offeredServiceType',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/offeredServiceType/offeredServiceType-dialog.html',
                        controller: 'OfferedServiceTypeDialogController',
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
                        $state.go('offeredServiceType', null, { reload: true });
                    }, function() {
                        $state.go('offeredServiceType');
                    })
                }]
            })
            .state('offeredServiceType.edit', {
                parent: 'offeredServiceType',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/offeredServiceType/offeredServiceType-dialog.html',
                        controller: 'OfferedServiceTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['OfferedServiceType', function(OfferedServiceType) {
                                return OfferedServiceType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('offeredServiceType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('offeredServiceType.delete', {
                parent: 'offeredServiceType',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/offeredServiceType/offeredServiceType-delete-dialog.html',
                        controller: 'OfferedServiceTypeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['OfferedServiceType', function(OfferedServiceType) {
                                return OfferedServiceType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('offeredServiceType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
