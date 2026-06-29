import { useEffect, useState } from "react";

import Modal from "../../components/Modal/Modal";
import VehiculoForm from "./VehiculoForm";
import VehiculoTable from "./VehiculoTable";

import {
    listarVehiculos,
    crearVehiculo,
    actualizarVehiculo,
    eliminarVehiculo
} from "../../services/vehiculoService";

import "./VehiculosPage.css";

function VehiculosPage() {

    const [vehiculos, setVehiculos] = useState([]);

    const [openModal, setOpenModal] = useState(false);

    const [vehiculoEdit, setVehiculoEdit] = useState(null);

    useEffect(() => {
        cargarVehiculos();
    }, []);

    const cargarVehiculos = async () => {
        const response = await listarVehiculos();
        setVehiculos(response.data);
    };

    // 🔥 ABRIR MODAL CREAR
    const handleOpenCreate = () => {
        setVehiculoEdit(null);
        setOpenModal(true);
    };

    // 🔥 ABRIR MODAL EDITAR
    const handleEdit = (vehiculo) => {
        setVehiculoEdit(vehiculo);
        setOpenModal(true);
    };

    // 🔥 GUARDAR (CREATE / UPDATE)
    const handleSubmit = async (vehiculo) => {

        if (vehiculoEdit) {
            await actualizarVehiculo(vehiculoEdit.id, vehiculo);
        } else {
            await crearVehiculo(vehiculo);
        }

        setOpenModal(false);
        setVehiculoEdit(null);
        cargarVehiculos();
    };

    // 🔥 ELIMINAR
    const handleDelete = async (id) => {
        await eliminarVehiculo(id);
        cargarVehiculos();
    };

    return (
        <div className="vehiculos-page">

            <div className="page-header">

                <h1>Vehículos</h1>

                <button onClick={handleOpenCreate}>
                    Nuevo Vehículo
                </button>

            </div>

            <VehiculoTable
                vehiculos={vehiculos}
                onEdit={handleEdit}
                onDelete={handleDelete}
            />

            <Modal
                isOpen={openModal}
                title={vehiculoEdit ? "Editar Vehículo" : "Nuevo Vehículo"}
                onClose={() => setOpenModal(false)}
            >

                <VehiculoForm
                    onSubmit={handleSubmit}
                    vehiculo={vehiculoEdit}
                />

            </Modal>

        </div>
    );
}

export default VehiculosPage;