import { useEffect, useState } from "react";

import Modal from "../../components/Modal/Modal";
import SoapForm from "./SoapForm";
import SoapTable from "./SoapTable";

import {
    listarSoap,
    crearSoap,
    actualizarSoap,
    eliminarSoap
} from "../../services/soapService";

function SoapPage() {

    const [soaps, setSoaps] = useState([]);
    const [open, setOpen] = useState(false);
    const [edit, setEdit] = useState(null);

    useEffect(() => {
        cargar();
    }, []);

    const cargar = async () => {

        const response = await listarSoap();

        setSoaps(response.data);

    };

    const handleSubmit = async (data) => {

        if (edit) {
            await actualizarSoap(edit.id, data);
        } else {
            await crearSoap(data);
        }

        setOpen(false);
        setEdit(null);

        cargar();

    };

    return (

        <div>

            <div className="page-header">

                <h1>SOAP</h1>

                <button
                    className="btn-primary"
                    onClick={() => {
                        setEdit(null);
                        setOpen(true);
                    }}
                >
                    Nuevo SOAP
                </button>

            </div>

            <SoapTable
                soaps={soaps}
                onEdit={(s) => {
                    setEdit(s);
                    setOpen(true);
                }}
                onDelete={async (id) => {
                    await eliminarSoap(id);
                    cargar();
                }}
            />

            <Modal
                isOpen={open}
                title={edit ? "Editar SOAP" : "Nuevo SOAP"}
                onClose={() => setOpen(false)}
            >
                <SoapForm
                    soap={edit}
                    onSubmit={handleSubmit}
                />
            </Modal>

        </div>

    );
}

export default SoapPage;