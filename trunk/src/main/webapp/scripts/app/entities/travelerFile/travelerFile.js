'use strict';

angular.module('zetravelcloudApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('travelerFile', {
                parent: 'entity',
                url: '/travelerFiles',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.travelerFile.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/travelerFile/travelerFiles.html',
                        controller: 'TravelerFileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('travelerFile');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('travelerFile.detail', {
                parent: 'entity',
                url: '/travelerFile/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.travelerFile.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/travelerFile/travelerFile-detail.html',
                        controller: 'TravelerFileDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('travelerFile');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TravelerFile', function($stateParams, TravelerFile) {
                        return TravelerFile.get({id : $stateParams.id});
                    }]
                }
            })
            .state('travelerFile.new', {
                parent: 'travelerFile',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/travelerFile/travelerFile-dialog.html',
                        controller: 'TravelerFileDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    fileName: null,
                                    file: null,
                                    fileContentType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('travelerFile', null, { reload: true });
                    }, function() {
                        $state.go('travelerFile');
                    })
                }]
            })
            .state('travelerFile.edit', {
                parent: 'travelerFile',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/travelerFile/travelerFile-dialog.html',
                        controller: 'TravelerFileDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TravelerFile', function(TravelerFile) {
                                return TravelerFile.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('travelerFile', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('travelerFile.delete', {
                parent: 'travelerFile',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/travelerFile/travelerFile-delete-dialog.html',
                        controller: 'TravelerFileDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TravelerFile', function(TravelerFile) {
                                return TravelerFile.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('travelerFile', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
